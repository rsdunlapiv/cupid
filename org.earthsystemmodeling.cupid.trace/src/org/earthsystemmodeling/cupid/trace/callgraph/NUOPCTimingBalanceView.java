package org.earthsystemmodeling.cupid.trace.callgraph;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.earthsystemmodeling.cupid.trace.Activator;
import org.earthsystemmodeling.cupid.trace.callstack.NUOPCCtfCallStackAnalysis;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceSelectedSignal;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceManager;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;
import org.eclipse.tracecompass.tmf.ui.viewers.tree.AbstractTmfTreeViewer;
import org.eclipse.tracecompass.tmf.ui.views.TmfView;

import com.google.common.base.Joiner;

public class NUOPCTimingBalanceView extends TmfView {

    public static final String ID = "org.earthsystemmodeling.cupid.trace.NUOPCTimingBalanceView";
    
	private static final int[] DEFAULT_WEIGHTS = new int[] { 4, 6 };

	private ITmfTrace fTrace;
    private @Nullable NUOPCStatisticsBalanceChartViewer fBalanceViewer;
    private @Nullable NUOPCGlobalStatisticsTreeViewer fTreeViewer;
    
    private final Semaphore fLock = new Semaphore(1);
    private Job fJob;

    
    public NUOPCTimingBalanceView() {
        super(ID);
    }

    /**
     * Used to keep the table in sync with the density viewer.
     */
    /*
    private final class DataChangedListener implements ISegmentStoreDensityViewerDataListener {

        private void updateTableModel(@Nullable Iterable<? extends ISegment> data) {
            final AbstractSegmentStoreTableViewer viewer = fTableViewer;
            if (viewer != null && data != null) {
                viewer.updateModel(data);
            }
        }

        @Override
        public void viewDataChanged(@NonNull Iterable<? extends @NonNull ISegment> newData) {
            updateTableModel(newData);
        }

        @Override
        public void selectedDataChanged(@Nullable Iterable<? extends @NonNull ISegment> newSelectionData) {
            updateTableModel(newSelectionData);
        }

    }
	*/
    
    @Override
    public void createPartControl(@Nullable Composite parent) {
        super.createPartControl(parent);

        final SashForm sashForm = new SashForm(parent, SWT.NONE);

        fTreeViewer = createStatisticsTreeViewer(sashForm);
        fBalanceViewer = createStatisticsBalanceChartViewer(sashForm);
        fTreeViewer.setBalanceChartViewer(fBalanceViewer);
        
        sashForm.setWeights(DEFAULT_WEIGHTS);

        //Action zoomOut = new ZoomOutAction(this);
        //IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
        //toolBar.add(zoomOut);
        
        ITmfTrace trace = TmfTraceManager.getInstance().getActiveTrace();
        if (trace != null) {
            TmfTraceSelectedSignal signal = new TmfTraceSelectedSignal(this, trace);
            traceSelected(signal);
            //if (fBalanceViewer != null) {
            //    fBalanceViewer.traceSelected(signal);
            //}
            //if (fTreeViewer != null) {
            //    fTreeViewer.traceSelected(signal);
            //}
        }
        
        getViewSite().getActionBars().getMenuManager().add(fExportAction);
    }
    
    @TmfSignalHandler
    public void traceSelected(final TmfTraceSelectedSignal signal) {
        fTrace = signal.getTrace();
        if (fTrace != null) {
            NUOPCCtfCallStackAnalysis analysisModule = TmfTraceUtils.getAnalysisModuleOfClass(fTrace, NUOPCCtfCallStackAnalysis.class, NUOPCCtfCallStackAnalysis.ID);
            initializeTreeViewer(analysisModule);
        }
    }
    
    
    protected void initializeTreeViewer(NUOPCCtfCallStackAnalysis analysisModule){
        
		Job job = fJob;
        if (job != null) {
            job.cancel();
        }
        try {
            fLock.acquire();
        } catch (InterruptedException e) {
            Activator.logWarning(e.getMessage(), e);
            fLock.release();
        }
        if (analysisModule == null) {
            getTreeViewer().setInput(null);
            fLock.release();
            return;
        }
        
        getTreeViewer().setInput(null);
        analysisModule.schedule();
        job = new Job("NUOPC Call Stack Analysis") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    analysisModule.waitForCompletion(monitor);
                    Display.getDefault().asyncExec(() -> {
                        getTreeViewer().setInput(analysisModule.getGlobalStatistics());
                     });
                    return Status.OK_STATUS;
                } finally {
                    fJob = null;
                    fLock.release();
                }
            }
        };
        fJob = job;
        job.schedule();
    }

    protected NUOPCGlobalStatisticsTreeViewer createStatisticsTreeViewer(Composite parent) {
    	return new NUOPCGlobalStatisticsTreeViewer(parent, this);
    }

    protected NUOPCStatisticsBalanceChartViewer createStatisticsBalanceChartViewer(Composite parent) {
    	return new NUOPCStatisticsBalanceChartViewer(parent);
    }

    @Override
    public void setFocus() {
        final NUOPCStatisticsBalanceChartViewer viewer = fBalanceViewer;
        if (viewer != null) {
            viewer.getControl().setFocus();
        }
    }

    @Override
    public void dispose() {
        final NUOPCStatisticsBalanceChartViewer balanceViewer = fBalanceViewer;
        if (balanceViewer != null) {
            balanceViewer.dispose();
        }

        final AbstractStatisticsTreeViewer treeViewer = fTreeViewer;
        if (treeViewer != null) {
            treeViewer.dispose();
        }

        super.dispose();
    }

  
    protected NUOPCStatisticsBalanceChartViewer getBalanceChartViewer() {
        return fBalanceViewer;
    }

   
    protected AbstractStatisticsTreeViewer getTreeViewer() {
        return fTreeViewer;
    }
    
    
    private final Action fExportAction = new ExportToTsvAction() {
        @Override
        protected void exportToTsv(@Nullable OutputStream stream) {
         
        	try (PrintWriter pw = new PrintWriter(stream)) {
                AbstractTmfTreeViewer statsViewer = fTreeViewer;
                if (statsViewer == null) {
                    return;
                }
                Tree tree = statsViewer.getTreeViewer().getTree();
                int size = tree.getItemCount();
                List<String> columns = new ArrayList<>();
                for (int i = 0; i < tree.getColumnCount(); i++) {
                    String valueOf = String.valueOf(tree.getColumn(i).getText());
                    if (valueOf.isEmpty() && i == tree.getColumnCount() - 1) {
                        // Linux "feature", an invisible column is added at the end
                        // with gtk2
                        break;
                    }
                    columns.add(valueOf);
                }
                String join = Joiner.on('\t').skipNulls().join(columns);
                pw.println(join);
                for (int i = 0; i < size; i++) {
                    TreeItem item = tree.getItem(i);
                    printItem(pw, columns, item, 0);
                }
            }
        	        
        }
        
        private void printItem(PrintWriter pw, List<String> columns, @Nullable TreeItem item, int depth) {
            if (item == null) {
                return;
            }
            List<String> data = new ArrayList<>();
            for (int col = 0; col < columns.size(); col++) {
            	String prefix = "";
            	//indent first column
            	if (col == 0) {
            		prefix = String.join("", Collections.nCopies(depth, " "));
            	}            	 
            	data.add(prefix + String.valueOf(item.getText(col)));
            }
            String line = Joiner.on('\t').join(data);
            if (line.trim().length() > 0) {
            	pw.println(Joiner.on('\t').join(data));
            }
            for (TreeItem child : item.getItems()) {
                printItem(pw, columns, child, depth+1);
            }
        }
        	
        @Override
        protected @Nullable Shell getShell() {
            return getViewSite().getShell();
        }

    };
    
}