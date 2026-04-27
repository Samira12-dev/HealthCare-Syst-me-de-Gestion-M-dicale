ALTER TABLE dossier_medical
    ADD CONSTRAINT fk_dossier_patient
        FOREIGN KEY (patient_id) REFERENCES patient(id);

ALTER TABLE rendez_vous
    ADD CONSTRAINT fk_rdv_patient
        FOREIGN KEY (patient_id) REFERENCES patient(id);

ALTER TABLE rendez_vous
    ADD CONSTRAINT fk_rdv_medecin
        FOREIGN KEY (medecin_id) REFERENCES medecin(id);

ALTER TABLE rendez_vous
    MODIFY statut ENUM('PLANIFIE','CONFIRME','ANNULE','TERMINE') NOT NULL;