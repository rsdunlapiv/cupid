/**
 */
package org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl;

import java.util.Collection;

import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCModel__ImplementsInitP2;
import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCModel__Init;
import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCModel__RealizesExportField;
import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCModel__RealizesImportField;
import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Implements Init P2</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getName <em>Name</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getImportParam <em>Import Param</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getExportParam <em>Export Param</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getRealizesImportField <em>Realizes Import Field</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#getRealizesExportField <em>Realizes Export Field</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#isRealizesAtLeastOneField <em>Realizes At Least One Field</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#isRegisteredInSetServices <em>Registered In Set Services</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.NUOPCModel__ImplementsInitP2Impl#isRealizesOrRegistered <em>Realizes Or Registered</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NUOPCModel__ImplementsInitP2Impl extends EObjectImpl implements NUOPCModel__ImplementsInitP2 {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "InitP2";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getImportParam() <em>Import Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportParam()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPORT_PARAM_EDEFAULT = "importState";

	/**
	 * The cached value of the '{@link #getImportParam() <em>Import Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportParam()
	 * @generated
	 * @ordered
	 */
	protected String importParam = IMPORT_PARAM_EDEFAULT;

	/**
	 * The default value of the '{@link #getExportParam() <em>Export Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportParam()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPORT_PARAM_EDEFAULT = "exportState";

	/**
	 * The cached value of the '{@link #getExportParam() <em>Export Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportParam()
	 * @generated
	 * @ordered
	 */
	protected String exportParam = EXPORT_PARAM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRealizesImportField() <em>Realizes Import Field</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealizesImportField()
	 * @generated
	 * @ordered
	 */
	protected EList<NUOPCModel__RealizesImportField> realizesImportField;

	/**
	 * The cached value of the '{@link #getRealizesExportField() <em>Realizes Export Field</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealizesExportField()
	 * @generated
	 * @ordered
	 */
	protected EList<NUOPCModel__RealizesExportField> realizesExportField;

	/**
	 * The cached setting delegate for the '{@link #isRealizesAtLeastOneField() <em>Realizes At Least One Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRealizesAtLeastOneField()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate REALIZES_AT_LEAST_ONE_FIELD__ESETTING_DELEGATE = ((EStructuralFeature.Internal)NUOPCPackage.Literals.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_AT_LEAST_ONE_FIELD).getSettingDelegate();

	/**
	 * The cached setting delegate for the '{@link #isRegisteredInSetServices() <em>Registered In Set Services</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRegisteredInSetServices()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate REGISTERED_IN_SET_SERVICES__ESETTING_DELEGATE = ((EStructuralFeature.Internal)NUOPCPackage.Literals.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REGISTERED_IN_SET_SERVICES).getSettingDelegate();

	/**
	 * The cached setting delegate for the '{@link #isRealizesOrRegistered() <em>Realizes Or Registered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRealizesOrRegistered()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate REALIZES_OR_REGISTERED__ESETTING_DELEGATE = ((EStructuralFeature.Internal)NUOPCPackage.Literals.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_OR_REGISTERED).getSettingDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NUOPCModel__ImplementsInitP2Impl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NUOPCPackage.Literals.NUOPC_MODEL_IMPLEMENTS_INIT_P2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NUOPCModel__Init getParent() {
		if (eContainerFeatureID() != NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT) return null;
		return (NUOPCModel__Init)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(NUOPCModel__Init newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(NUOPCModel__Init newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, NUOPCPackage.NUOPC_MODEL_INIT__IMPLEMENTS_INIT_P2, NUOPCModel__Init.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImportParam() {
		return importParam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportParam(String newImportParam) {
		String oldImportParam = importParam;
		importParam = newImportParam;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__IMPORT_PARAM, oldImportParam, importParam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExportParam() {
		return exportParam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExportParam(String newExportParam) {
		String oldExportParam = exportParam;
		exportParam = newExportParam;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__EXPORT_PARAM, oldExportParam, exportParam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NUOPCModel__RealizesImportField> getRealizesImportField() {
		if (realizesImportField == null) {
			realizesImportField = new EObjectContainmentWithInverseEList<NUOPCModel__RealizesImportField>(NUOPCModel__RealizesImportField.class, this, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD, NUOPCPackage.NUOPC_MODEL_REALIZES_IMPORT_FIELD__PARENT);
		}
		return realizesImportField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NUOPCModel__RealizesExportField> getRealizesExportField() {
		if (realizesExportField == null) {
			realizesExportField = new EObjectContainmentWithInverseEList<NUOPCModel__RealizesExportField>(NUOPCModel__RealizesExportField.class, this, NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD, NUOPCPackage.NUOPC_MODEL_REALIZES_EXPORT_FIELD__PARENT);
		}
		return realizesExportField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRealizesAtLeastOneField() {
		return (Boolean)REALIZES_AT_LEAST_ONE_FIELD__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRealizesAtLeastOneField(boolean newRealizesAtLeastOneField) {
		REALIZES_AT_LEAST_ONE_FIELD__ESETTING_DELEGATE.dynamicSet(this, null, 0, newRealizesAtLeastOneField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRegisteredInSetServices() {
		return (Boolean)REGISTERED_IN_SET_SERVICES__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRegisteredInSetServices(boolean newRegisteredInSetServices) {
		REGISTERED_IN_SET_SERVICES__ESETTING_DELEGATE.dynamicSet(this, null, 0, newRegisteredInSetServices);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRealizesOrRegistered() {
		return (Boolean)REALIZES_OR_REGISTERED__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRealizesOrRegistered(boolean newRealizesOrRegistered) {
		REALIZES_OR_REGISTERED__ESETTING_DELEGATE.dynamicSet(this, null, 0, newRealizesOrRegistered);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((NUOPCModel__Init)otherEnd, msgs);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRealizesImportField()).basicAdd(otherEnd, msgs);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRealizesExportField()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				return basicSetParent(null, msgs);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				return ((InternalEList<?>)getRealizesImportField()).basicRemove(otherEnd, msgs);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				return ((InternalEList<?>)getRealizesExportField()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				return eInternalContainer().eInverseRemove(this, NUOPCPackage.NUOPC_MODEL_INIT__IMPLEMENTS_INIT_P2, NUOPCModel__Init.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				return getParent();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__NAME:
				return getName();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__IMPORT_PARAM:
				return getImportParam();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__EXPORT_PARAM:
				return getExportParam();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				return getRealizesImportField();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				return getRealizesExportField();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_AT_LEAST_ONE_FIELD:
				return isRealizesAtLeastOneField();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REGISTERED_IN_SET_SERVICES:
				return isRegisteredInSetServices();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_OR_REGISTERED:
				return isRealizesOrRegistered();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				setParent((NUOPCModel__Init)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__NAME:
				setName((String)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__IMPORT_PARAM:
				setImportParam((String)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__EXPORT_PARAM:
				setExportParam((String)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				getRealizesImportField().clear();
				getRealizesImportField().addAll((Collection<? extends NUOPCModel__RealizesImportField>)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				getRealizesExportField().clear();
				getRealizesExportField().addAll((Collection<? extends NUOPCModel__RealizesExportField>)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_AT_LEAST_ONE_FIELD:
				setRealizesAtLeastOneField((Boolean)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REGISTERED_IN_SET_SERVICES:
				setRegisteredInSetServices((Boolean)newValue);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_OR_REGISTERED:
				setRealizesOrRegistered((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				setParent((NUOPCModel__Init)null);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__NAME:
				setName(NAME_EDEFAULT);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__IMPORT_PARAM:
				setImportParam(IMPORT_PARAM_EDEFAULT);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__EXPORT_PARAM:
				setExportParam(EXPORT_PARAM_EDEFAULT);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				getRealizesImportField().clear();
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				getRealizesExportField().clear();
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_AT_LEAST_ONE_FIELD:
				REALIZES_AT_LEAST_ONE_FIELD__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REGISTERED_IN_SET_SERVICES:
				REGISTERED_IN_SET_SERVICES__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_OR_REGISTERED:
				REALIZES_OR_REGISTERED__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__PARENT:
				return getParent() != null;
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__IMPORT_PARAM:
				return IMPORT_PARAM_EDEFAULT == null ? importParam != null : !IMPORT_PARAM_EDEFAULT.equals(importParam);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__EXPORT_PARAM:
				return EXPORT_PARAM_EDEFAULT == null ? exportParam != null : !EXPORT_PARAM_EDEFAULT.equals(exportParam);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_IMPORT_FIELD:
				return realizesImportField != null && !realizesImportField.isEmpty();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_EXPORT_FIELD:
				return realizesExportField != null && !realizesExportField.isEmpty();
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_AT_LEAST_ONE_FIELD:
				return REALIZES_AT_LEAST_ONE_FIELD__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REGISTERED_IN_SET_SERVICES:
				return REGISTERED_IN_SET_SERVICES__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case NUOPCPackage.NUOPC_MODEL_IMPLEMENTS_INIT_P2__REALIZES_OR_REGISTERED:
				return REALIZES_OR_REGISTERED__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", importParam: ");
		result.append(importParam);
		result.append(", exportParam: ");
		result.append(exportParam);
		result.append(')');
		return result.toString();
	}

} //NUOPCModel__ImplementsInitP2Impl
