-- Fix user_details.id type to BIGINT (was INT in earlier migration)

-- Fix patient table
ALTER TABLE patient DROP COLUMN email;
ALTER TABLE patient
    ADD CONSTRAINT fk_patient_user_details
        FOREIGN KEY (id) REFERENCES user(id);

-- Fix medecine table
ALTER TABLE medecin DROP COLUMN email;
ALTER TABLE medecin
    ADD CONSTRAINT fk_medecin_user
        FOREIGN KEY (id) REFERENCES user(id);



CREATE TABLE admin (
                       id BIGINT PRIMARY KEY,
                       CONSTRAINT fk_user_admin
                           FOREIGN KEY (id) REFERENCES  user(id)
);