package org.earthsystemmodeling.cupid.trace;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.statesystem.core.exceptions.AttributeNotFoundException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateValueTypeException;
import org.eclipse.tracecompass.statesystem.core.exceptions.TimeRangeException;
import org.eclipse.tracecompass.statesystem.core.statevalue.ITmfStateValue;
import org.eclipse.tracecompass.statesystem.core.statevalue.TmfStateValue;
import org.eclipse.tracecompass.statesystem.core.statevalue.ITmfStateValue.Type;
import org.eclipse.tracecompass.tmf.core.callstack.CallStackStateProvider;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.ITmfEventField;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;
import org.eclipse.tracecompass.tmf.ctf.core.CtfEnumPair;
import org.eclipse.tracecompass.tmf.ctf.core.event.CtfTmfEvent;
import org.eclipse.tracecompass.tmf.ctf.core.trace.CtfTmfTrace;

public abstract class NUOPCCtfCallStackStateProvider extends CallStackStateProvider {

	public static final int REGION_ENTER = 0;
	public static final int REGION_EXIT = 1;
	
	public static final int ESMF_METHOD_INIT = 0;
	public static final int ESMF_METHOD_RUN = 1;
	public static final int ESMF_METHOD_FINAL = 2;
			
	protected NUOPCCtfStateSystemAnalysisModule stateAnalysis;
			
	public NUOPCCtfCallStackStateProvider(CtfTmfTrace trace) {
		super(trace);
		stateAnalysis = TmfTraceUtils.getAnalysisModuleOfClass(trace, NUOPCCtfStateSystemAnalysisModule.class, NUOPCCtfStateSystemAnalysisModule.ID);				
	}
	
	public static NUOPCCtfCallStackStateProvider newInstance(CtfTmfTrace trace) {
		
		NUOPCCtfTrace trc = (NUOPCCtfTrace) trace;
		
		if (trc.getTraceVersion() ==  0.1) {
			return new Version0P1(trace);
		}
		else if (trc.getTraceVersion() >= 0.2) {
			return new Version0P2(trace);
		}
		else {
			Activator.logWarning("Unsupported ESMF trace version:  " + trc.getTraceVersion());
			return null;
		}
	}
	
	@Override
	public int getVersion() {
		return 0;
	}
	
		
	@Override
	protected boolean considerEvent(ITmfEvent event) {
		String ename = event.getType().getName();
		return ename.equals("region");			
	}
		
	protected abstract String getComponentKind(ITmfEvent event);
	
	@Override
	protected void eventHandle(ITmfEvent event) {
		
		if (!considerEvent(event)) {
            return;
        }
		
        ITmfStateSystemBuilder ss = getStateSystemBuilder();
        if (ss == null) return;

        /* Check if the event is a function entry */
        ITmfStateValue functionEntryName = functionEntry(event);
        if (functionEntryName != null) {
            long timestamp = event.getTimestamp().toNanos();

            String processName = getProcessName(event);
            int processId = getProcessId(event);
            if (processName == null) {
                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
            }
            int processQuark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName);
            ss.updateOngoingState(TmfStateValue.newValueInt(processId), processQuark);

            String threadName = getThreadName(event);
            long threadId = getThreadId(event);
            if (threadName == null) {
                threadName = Long.toString(threadId);
            }
            int threadQuark = ss.getQuarkRelativeAndAdd(processQuark, threadName);
            ss.updateOngoingState(TmfStateValue.newValueLong(threadId), threadQuark);

            int callStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, CALL_STACK);
            ITmfStateValue value = functionEntryName;
            ss.pushAttribute(timestamp, value, callStackQuark);
                                   
            //add component kind
            String kind = getComponentKind(event);
            int compkindQuark = ss.getQuarkRelativeAndAdd(threadQuark, "compkind");
        	ITmfStateValue toPush = TmfStateValue.newValueString("Unknown");
        	if (kind != null) {
        		toPush = TmfStateValue.newValueString(kind);
        	}
        	ss.pushAttribute(timestamp, toPush, compkindQuark);
        	
        	//add clock
        	/*
        	String currTime = getComponentClock(event);
        	int compClockQuark = ss.getQuarkRelativeAndAdd(threadQuark, "clock");
        	toPush = TmfStateValue.newValueString("Unknown");
        	if (kind != null) {
        		toPush = TmfStateValue.newValueString(kind);
        	}
        	ss.pushAttribute(timestamp, toPush, compkindQuark);
        	*/
        	
            return;
        }

        /* Check if the event is a function exit */
        ITmfStateValue functionExitState = functionExit(event);
        if (functionExitState != null) {
            long timestamp = event.getTimestamp().toNanos();
            String processName = getProcessName(event);
            if (processName == null) {
                int processId = getProcessId(event);
                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
            }
            String threadName = getThreadName(event);
            if (threadName == null) {
                threadName = Long.toString(getThreadId(event));
            }
            int quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, CALL_STACK);
            ITmfStateValue poppedValue = ss.popAttribute(timestamp, quark);
            /*
             * Verify that the value we are popping matches the one in the
             * event field, unless the latter is undefined.
             */
            if (!functionExitState.isNull() && !functionExitState.equals(poppedValue)) {
                //TODO: error logging
            	Activator.logWarning("Call stack popped value does not match.  Possible missing event.");
            }
            
            quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "compkind");
            ss.popAttribute(timestamp, quark);
        }
		
	}
			
	protected String getFuncName(ITmfEvent event) {
		if (event.getType().getName().equals("region")) {
			return event.getContent().getFieldValue(String.class, "name");
		}
		return null;
	}
	
	@Override
	protected @Nullable ITmfStateValue functionEntry(ITmfEvent event) {
		if (event.getType().getName().equals("region")) {
			ITmfEventField ctrl = event.getContent().getField("ctrl");
			CtfEnumPair v = (CtfEnumPair) ctrl.getValue();
			if (v.getLongValue() == REGION_ENTER) {
				String name = event.getContent().getFieldValue(String.class, "name");
				if (name == null) return null;
				return TmfStateValue.newValueString(name);
			}
		}
		return null;
	}

	@Override
	protected @Nullable ITmfStateValue functionExit(ITmfEvent event) {
		if (event.getType().getName().equals("region")) {
			ITmfEventField ctrl = event.getContent().getField("ctrl");
			CtfEnumPair v = (CtfEnumPair) ctrl.getValue();
			if (v.getLongValue() == REGION_EXIT) {
				String name = event.getContent().getFieldValue(String.class, "name");
				if (name == null) return null;
				return TmfStateValue.newValueString(name);
			}			
		}
		return null;
	}

	@Override
	protected int getProcessId(ITmfEvent event) {
		CtfTmfEvent e = (CtfTmfEvent) event;
		return ((Long) e.getPacketAttributes().get("pet")).intValue();		
	}

	@Override
	protected long getThreadId(ITmfEvent event) {
		return 0;
	}
	
	
	
	////////////////////  version 0.1 trace /////////////////////
	
	public static class Version0P1 extends NUOPCCtfCallStackStateProvider {

		public Version0P1(CtfTmfTrace trace) {
			super(trace);
		}
		
		@Override
		public NUOPCCtfCallStackStateProvider getNewInstance() {
			return new Version0P1((CtfTmfTrace) getTrace());			
		}
		
		@Override
		protected boolean considerEvent(ITmfEvent event) {
			String ename = event.getType().getName();
			return ename.equals("control") || super.considerEvent(event);			
		}
		
		@Override
		protected String getComponentKind(ITmfEvent event) {
            Long vmid = event.getContent().getFieldValue(Long.class, "vmid");
            Long baseid = event.getContent().getFieldValue(Long.class, "baseid");
            String kind = null;
            if (vmid != null && baseid != null) {  /* could be a region */
            	kind = stateAnalysis.queryComponentKind(vmid, baseid);
            }
        	return kind;
		}
		
		@Override
		protected @Nullable ITmfStateValue functionEntry(ITmfEvent event) {
			ITmfEventField ctrl = event.getContent().getField("ctrl");
			CtfEnumPair v = (CtfEnumPair) ctrl.getValue();
			if (v.getLongValue() == 0) {
				String name = getFuncName(event);
				if (name == null) return null;
				return TmfStateValue.newValueString(name);
			}
			return super.functionEntry(event);
		}
		
		@Override
		protected @Nullable ITmfStateValue functionExit(ITmfEvent event) {
			ITmfEventField ctrl = event.getContent().getField("ctrl");
			CtfEnumPair v = (CtfEnumPair) ctrl.getValue();
			if (v.getLongValue() == 1) {
				String name = getFuncName(event);
				if (name == null) return null;
				return TmfStateValue.newValueString(name);
			}
			return super.functionEntry(event);
		}
		
		@Override
		protected String getFuncName(ITmfEvent event) {
			
			if (event.getType().getName().equals("control")) {
				long vmid   = event.getContent().getFieldValue(Long.class, "vmid");
				long baseid = event.getContent().getFieldValue(Long.class, "baseid");
				CtfEnumPair mp = event.getContent().getFieldValue(CtfEnumPair.class, "method");
				long phase  = event.getContent().getFieldValue(Long.class, "phase");
				
				String compName = stateAnalysis.queryComponentName(vmid, baseid);
				if (compName == null) {
					compName = "Unknown";
				}
				
				String phaseLabel = null;
				phaseLabel = stateAnalysis.queryComponentPhaseLabel(vmid, baseid, mp.getLongValue(), phase);
						
				if (phaseLabel == null) {
					String method = "";
					if (mp.getLongValue() == ESMF_METHOD_INIT) {
						method = "init ";
					}
					else if (mp.getLongValue() == ESMF_METHOD_RUN) {
						method = "run ";
					}
					else if (mp.getLongValue() == ESMF_METHOD_FINAL) {
						method = "final ";
					}
					phaseLabel = method + "#" + String.valueOf(phase);
				}
		
				return "[" + compName + "] " + phaseLabel;
			}
			else {
				return super.getFuncName(event);
			}
		}
		
	}
	
	
	//////////////////// version 0.2 trace //////////////////////
	

	public static class Version0P2 extends NUOPCCtfCallStackStateProvider {

		public Version0P2(CtfTmfTrace trace) {
			super(trace);
		}
		
		@Override
		public NUOPCCtfCallStackStateProvider getNewInstance() {
			return new Version0P2((CtfTmfTrace) getTrace());			
		}
		
		@Override
		protected void eventHandle(ITmfEvent event) {

			if (!considerEvent(event)) {
	            return;
	        }
			
			ITmfStateSystemBuilder ss = getStateSystemBuilder();
	        if (ss == null) return;
			
			String ename = event.getType().getName();
			if (ename.equals("prologue")) {
				long timestamp = event.getTimestamp().toNanos();

	            String processName = getProcessName(event);
	            int processId = getProcessId(event);
	            if (processName == null) {
	                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
	            }
	            int processQuark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName);
	            ss.updateOngoingState(TmfStateValue.newValueInt(processId), processQuark);

	            String threadName = getThreadName(event);
	            long threadId = getThreadId(event);
	            if (threadName == null) {
	                threadName = Long.toString(threadId);
	            }
	            int threadQuark = ss.getQuarkRelativeAndAdd(processQuark, threadName);
	            ss.updateOngoingState(TmfStateValue.newValueLong(threadId), threadQuark);

	            long vmid   = event.getContent().getFieldValue(Long.class, "vmid");
				long baseid = event.getContent().getFieldValue(Long.class, "baseid");
				CtfEnumPair mp = event.getContent().getFieldValue(CtfEnumPair.class, "method");
				long phase  = event.getContent().getFieldValue(Long.class, "phase");
				
				//push the vmid, baseid, method, and phase
	            int proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "vmid");
	            ITmfStateValue value = TmfStateValue.newValueLong(vmid);
	            ss.pushAttribute(timestamp, value, proStackQuark);
	            
	            proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "baseid");
	            value = TmfStateValue.newValueLong(baseid);
	            ss.pushAttribute(timestamp, value, proStackQuark);
	            
	            proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "method");
	            value = TmfStateValue.newValueLong(mp.getLongValue().intValue());
	            ss.pushAttribute(timestamp, value, proStackQuark);
	            
	            proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "phase");
	            value = TmfStateValue.newValueLong(phase);
	            ss.pushAttribute(timestamp, value, proStackQuark);	            
	            
			}
			else if (ename.equals("epilogue")) {
				
				long timestamp = event.getTimestamp().toNanos();
	            String processName = getProcessName(event);
	            if (processName == null) {
	                int processId = getProcessId(event);
	                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
	            }
	            String threadName = getThreadName(event);
	            if (threadName == null) {
	                threadName = Long.toString(getThreadId(event));
	            }
	            
	            //sanity check popped values
	            long vmid   = event.getContent().getFieldValue(Long.class, "vmid");
				long baseid = event.getContent().getFieldValue(Long.class, "baseid");
				CtfEnumPair mp = event.getContent().getFieldValue(CtfEnumPair.class, "method");
				long phase  = event.getContent().getFieldValue(Long.class, "phase");
	            	            
	            int quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "PrologueEpilogue", "vmid");	            
	            ITmfStateValue poppedValue = ss.popAttribute(timestamp, quark);
	            if (!poppedValue.isNull() && poppedValue.unboxLong() != vmid) {
	            	Activator.logWarning("Call stack popped value does not match.  Possible missing event.");
	            }
	        
	            quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "PrologueEpilogue", "baseid");	            
	            poppedValue = ss.popAttribute(timestamp, quark);
	            if (!poppedValue.isNull() && poppedValue.unboxLong() != baseid) {
	            	Activator.logWarning("Call stack popped value does not match.  Possible missing event.");
	            }
	        
	            quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "PrologueEpilogue", "method");	            
	            poppedValue = ss.popAttribute(timestamp, quark);
	            if (!poppedValue.isNull() && poppedValue.unboxLong() != mp.getLongValue()) {
	            	Activator.logWarning("Call stack popped value does not match.  Possible missing event.");
	            }
	            
	            quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "PrologueEpilogue", "phase");	            
	            poppedValue = ss.popAttribute(timestamp, quark);
	            if (!poppedValue.isNull() && poppedValue.unboxLong() != phase) {
	            	Activator.logWarning("Call stack popped value does not match.  Possible missing event.");
	            }
	            
			}
			else if (event.getType().getName().equals("clk")) {
            	
				long timestamp = event.getTimestamp().toNanos();
	            String processName = getProcessName(event);
	            if (processName == null) {
	                int processId = getProcessId(event);
	                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
	            }
	            String threadName = getThreadName(event);
	            if (threadName == null) {
	                threadName = Long.toString(getThreadId(event));
	            }
				            	
            	long year = event.getContent().getFieldValue(Long.class, "year");
            	long month = event.getContent().getFieldValue(Long.class, "month");
            	long day = event.getContent().getFieldValue(Long.class, "day");
            	long hour = event.getContent().getFieldValue(Long.class, "hour");
            	long minute = event.getContent().getFieldValue(Long.class, "minute");
            	long second = event.getContent().getFieldValue(Long.class, "second");
            	String monthStr = String.valueOf(month);
            	if (monthStr.length() == 1) monthStr = "0"+monthStr;
            	String dayStr = String.valueOf(day);
            	if (dayStr.length() == 1) dayStr = "0"+dayStr;
            	String hourStr = String.valueOf(hour);
            	if (hourStr.length() == 1) hourStr = "0"+hourStr;
            	String minuteStr = String.valueOf(minute);
            	if (minuteStr.length() == 1) minuteStr = "0"+minuteStr;
            	String secondStr = String.valueOf(second);
            	if (secondStr.length() == 1) secondStr = "0"+secondStr;
            	
            	
            	String timeStr = "" + year + monthStr + dayStr + "T" + hourStr + ":" + minuteStr + ":" + secondStr;
            	TmfStateValue.newValueString(timeStr);
            	
            	int quark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName, threadName, "clock");	            
            	ss.modifyAttribute(timestamp, TmfStateValue.newValueString(timeStr), quark);
            	//ss.updateOngoingState(TmfStateValue.newValueString(timeStr), quark);
            	
            }
			else {
				super.eventHandle(event);
			}
			
		}
		
		@Override
		protected boolean considerEvent(ITmfEvent event) {
			String ename = event.getType().getName();
			return ename.equals("prologue") || ename.equals("epilogue") ||
					ename.equals("phase_start") || ename.equals("phase_end") ||
					ename.equals("region") || ename.equals("clk") || 
					super.considerEvent(event);			
		}
		
		@Override
		protected String getComponentKind(ITmfEvent event) {
            		
			ITmfStateSystemBuilder ss = getStateSystemBuilder();
	        if (ss == null) return null;
				        
	        String processName = getProcessName(event);
            int processId = getProcessId(event);
            if (processName == null) {
                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
            }
            int processQuark = ss.getQuarkAbsoluteAndAdd(PROCESSES, processName);

            String threadName = getThreadName(event);
            long threadId = getThreadId(event);
            if (threadName == null) {
                threadName = Long.toString(threadId);
            }
            int threadQuark = ss.getQuarkRelativeAndAdd(processQuark, threadName);
	        	        
	        int proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "vmid");
            ITmfStateValue value = peekAttribute(proStackQuark);
            if (value == null || value.isNull()) return null;
            long vmid = value.unboxLong();
            
            proStackQuark = ss.getQuarkRelativeAndAdd(threadQuark, "PrologueEpilogue", "baseid");
            value = peekAttribute(proStackQuark);
            if (value == null || value.isNull()) return null;
            long baseid = value.unboxLong();
			
			return stateAnalysis.queryComponentKind(vmid, baseid);
            
		}
				
	    protected ITmfStateValue peekAttribute(int attributeQuark)
	            throws TimeRangeException, StateValueTypeException {
	        
	    	ITmfStateSystemBuilder ss = getStateSystemBuilder();
	        if (ss == null) return null;
	        
	    	/* These are the state values of the stack-attribute itself */
	        ITmfStateValue previousSV = ss.queryOngoingState(attributeQuark);

	        if (previousSV.isNull()) {
	            /*
	             * Trying to peek an empty stack. 
	             */
	            return null;
	        }
	        if (previousSV.getType() != Type.INTEGER) {
	            /*
	             * The existing value was not an integer (which is expected for
	             * stack tops), this doesn't look like a valid stack attribute.
	             */
	            throw new StateValueTypeException(ss.getSSID() + " Quark:" + attributeQuark + ", Type:" + previousSV.getType() + ", Expected:" + Type.INTEGER); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        }

	        int stackDepth = previousSV.unboxInt();

	        if (stackDepth <= 0) {
	            /* This on the other hand should not happen... */
	            throw new StateValueTypeException(ss.getSSID() + " Quark:" + attributeQuark + ", Stack depth:" + stackDepth);  //$NON-NLS-1$//$NON-NLS-2$
	        }

	        /* The attribute should already exist at this point */
	        int subAttributeQuark;
	        try {
	            subAttributeQuark = ss.getQuarkRelative(attributeQuark, String.valueOf(stackDepth));
	        } catch (AttributeNotFoundException e) {
	            String message = " Stack attribute missing sub-attribute for depth:" + stackDepth; //$NON-NLS-1$
	            throw new IllegalStateException(ss.getSSID() + " Quark:" + attributeQuark + message); //$NON-NLS-1$
	        }
	        ITmfStateValue peekedValue = ss.queryOngoingState(subAttributeQuark);
   	        return peekedValue;
	    }
		
		@Override
		protected @Nullable ITmfStateValue functionEntry(ITmfEvent event) {
			if (event.getType().getName().equals("phase_start")) {
				try {
				
					ITmfStateSystemBuilder ss = getStateSystemBuilder();
			        if (ss == null) return null;
			        
			        String processName = getProcessName(event);
		            int processId = getProcessId(event);
		            if (processName == null) {
		                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
		            }
		            int processQuark = ss.getQuarkAbsolute(PROCESSES, processName);
	
		            String threadName = getThreadName(event);
		            long threadId = getThreadId(event);
		            if (threadName == null) {
		                threadName = Long.toString(threadId);
		            }
		            int threadQuark = ss.getQuarkRelative(processQuark, threadName);
	
		            int proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "vmid");
		            ITmfStateValue value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long vmid = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "baseid");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long baseid = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "method");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long method = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "phase");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long phase = value.unboxLong();
		            
		            return TmfStateValue.newValueString(getFuncName(vmid, baseid, method, phase));
		        	
				}
				catch (AttributeNotFoundException e) {
					return null;
				}	            
				
			}
			return super.functionEntry(event);
		}

		@Override
		protected @Nullable ITmfStateValue functionExit(ITmfEvent event) {
			
			if (event.getType().getName().equals("phase_end")) {
				try {
				
					ITmfStateSystemBuilder ss = getStateSystemBuilder();
			        if (ss == null) return null;
			        
			        String processName = getProcessName(event);
		            int processId = getProcessId(event);
		            if (processName == null) {
		                processName = (processId == UNKNOWN_PID) ? UNKNOWN : Integer.toString(processId);
		            }
		            int processQuark = ss.getQuarkAbsolute(PROCESSES, processName);
	
		            String threadName = getThreadName(event);
		            long threadId = getThreadId(event);
		            if (threadName == null) {
		                threadName = Long.toString(threadId);
		            }
		            int threadQuark = ss.getQuarkRelative(processQuark, threadName);
	
		            int proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "vmid");
		            ITmfStateValue value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long vmid = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "baseid");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long baseid = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "method");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long method = value.unboxLong();
		            
		            proStackQuark = ss.getQuarkRelative(threadQuark, "PrologueEpilogue", "phase");
		            value = peekAttribute(proStackQuark);
		            if (value == null || value.isNull()) return null;
		            long phase = value.unboxLong();
		            
		            return TmfStateValue.newValueString(getFuncName(vmid, baseid, method, phase));
				}
				catch (AttributeNotFoundException e) {
					return null;
				}	            
			}
			return super.functionExit(event);
		}
		
		
		protected String getFuncName(long vmid, long baseid, long method, long phase) {
			String compName = stateAnalysis.queryComponentName(vmid, baseid);
			if (compName == null) {
				compName = "Unknown";
			}
			
			String phaseLabel = null;
			phaseLabel = stateAnalysis.queryComponentPhaseLabel(vmid, baseid, method, phase);
					
			if (phaseLabel == null) {
				String methodStr = "";
				if (method == ESMF_METHOD_INIT) {
					methodStr = "init ";
				}
				else if (method == ESMF_METHOD_RUN) {
					methodStr = "run ";
				}
				else if (method == ESMF_METHOD_FINAL) {
					methodStr = "final ";
				}
				phaseLabel = methodStr + "#" + String.valueOf(phase);
			}
			return "[" + compName + "] " + phaseLabel;
		}
		
		
	}
	
}
