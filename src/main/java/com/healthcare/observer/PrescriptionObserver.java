package com.healthcare.observer;

import com.healthcare.model.Prescription;

public interface PrescriptionObserver {
    void onPrescriptionGenerated(Prescription prescription);
}
