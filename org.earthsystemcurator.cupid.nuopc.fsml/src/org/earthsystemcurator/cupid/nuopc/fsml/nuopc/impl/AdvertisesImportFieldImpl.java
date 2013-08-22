/**
 */
package org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl;

import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.AdvertisesImportField;
import org.earthsystemcurator.cupid.nuopc.fsml.nuopc.NUOPCPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advertises Import Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.AdvertisesImportFieldImpl#isAddsToImportState <em>Adds To Import State</em>}</li>
 *   <li>{@link org.earthsystemcurator.cupid.nuopc.fsml.nuopc.impl.AdvertisesImportFieldImpl#getStandardName <em>Standard Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AdvertisesImportFieldImpl extends EObjectImpl implements AdvertisesImportField {
	/**
	 * The default value of the '{@link #isAddsToImportState() <em>Adds To Import State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAddsToImportState()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ADDS_TO_IMPORT_STATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAddsToImportState() <em>Adds To Import State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAddsToImportState()
	 * @generated
	 * @ordered
	 */
	protected boolean addsToImportState = ADDS_TO_IMPORT_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStandardName() <em>Standard Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStandardName()
	 * @generated
	 * @ordered
	 */
	protected static final String STANDARD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStandardName() <em>Standard Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStandardName()
	 * @generated
	 * @ordered
	 */
	protected String standardName = STANDARD_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdvertisesImportFieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NUOPCPackage.Literals.ADVERTISES_IMPORT_FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAddsToImportState() {
		return addsToImportState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddsToImportState(boolean newAddsToImportState) {
		boolean oldAddsToImportState = addsToImportState;
		addsToImportState = newAddsToImportState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.ADVERTISES_IMPORT_FIELD__ADDS_TO_IMPORT_STATE, oldAddsToImportState, addsToImportState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStandardName() {
		return standardName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStandardName(String newStandardName) {
		String oldStandardName = standardName;
		standardName = newStandardName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NUOPCPackage.ADVERTISES_IMPORT_FIELD__STANDARD_NAME, oldStandardName, standardName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__ADDS_TO_IMPORT_STATE:
				return isAddsToImportState();
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__STANDARD_NAME:
				return getStandardName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__ADDS_TO_IMPORT_STATE:
				setAddsToImportState((Boolean)newValue);
				return;
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__STANDARD_NAME:
				setStandardName((String)newValue);
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
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__ADDS_TO_IMPORT_STATE:
				setAddsToImportState(ADDS_TO_IMPORT_STATE_EDEFAULT);
				return;
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__STANDARD_NAME:
				setStandardName(STANDARD_NAME_EDEFAULT);
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
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__ADDS_TO_IMPORT_STATE:
				return addsToImportState != ADDS_TO_IMPORT_STATE_EDEFAULT;
			case NUOPCPackage.ADVERTISES_IMPORT_FIELD__STANDARD_NAME:
				return STANDARD_NAME_EDEFAULT == null ? standardName != null : !STANDARD_NAME_EDEFAULT.equals(standardName);
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
		result.append(" (addsToImportState: ");
		result.append(addsToImportState);
		result.append(", standardName: ");
		result.append(standardName);
		result.append(')');
		return result.toString();
	}

} //AdvertisesImportFieldImpl
