CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE candidates (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_candidate_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE recruiters (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    company varchar(255) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_recruiter_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE jobs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title varchar(255) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT NOT NULL,
    recruiter_id BIGINT NOT NULL,
    status varchar(50) DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_recruiter FOREIGN KEY (recruiter_id) REFERENCES recruiters(id)
);

CREATE TABLE applications (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,

    resume_filename varchar(255) NOT NULL,
    ai_feedback JSONB,
    status varchar(50) DEFAULT 'RECEIVED',
    match_percentage int,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_application_candidate FOREIGN KEY(candidate_id) REFERENCES candidates(id),
    CONSTRAINT fk_application_job FOREIGN KEY(job_id) REFERENCES jobs(id),
    CONSTRAINT uk_unique_application UNIQUE(candidate_id, job_id)
);