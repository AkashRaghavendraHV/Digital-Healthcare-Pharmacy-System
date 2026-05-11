package com.healthcare.service;

import com.healthcare.model.Patient;
import com.healthcare.model.Prescription;
import com.healthcare.observer.PrescriptionObserver;
import com.healthcare.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final List<PrescriptionObserver> observers;

    public List<Prescription> getPatientPrescriptions(Patient patient) {
        return prescriptionRepository.findByPatientOrderByIssuedAtDesc(patient);
    }
    
    public List<Prescription> getUnfulfilledPrescriptions(Patient patient) {
        return prescriptionRepository.findByPatientAndIsFulfilledFalseOrderByIssuedAtDesc(patient);
    }

    @Transactional
    public Prescription generatePrescription(Prescription prescription) {
        Prescription saved = prescriptionRepository.save(prescription);
        notifyObservers(saved);
        return saved;
    }

    private void notifyObservers(Prescription prescription) {
        for (PrescriptionObserver observer : observers) {
            observer.onPrescriptionGenerated(prescription);
        }
    }
}
