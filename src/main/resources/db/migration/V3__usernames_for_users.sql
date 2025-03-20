ALTER TABLE issue_tracker.users
ADD COLUMN username VARCHAR(100) UNIQUE NOT NULL;

CREATE TABLE IF NOT EXISTS issue_tracker.roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS issue_tracker.user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES issue_tracker.users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES issue_tracker.roles(id) ON DELETE CASCADE
);