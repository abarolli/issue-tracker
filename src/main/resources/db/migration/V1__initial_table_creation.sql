
CREATE TABLE IF NOT EXISTS issue_tracker.users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS issue_tracker.issues (
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	status VARCHAR(20) NOT NULL CHECK (status IN ('open', 'in_progress', 'resolved', 'closed')),
	priority VARCHAR(10) NOT NULL CHECK (priority IN ('low', 'medium', 'high', 'critical'))
);

CREATE TABLE IF NOT EXISTS issue_tracker.issue_assignees (
	issue_id INT NOT NULL,
	user_id INT NOT NULL,
	assigned_at TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (issue_id, user_id),
	FOREIGN KEY (issue_id) REFERENCES issue_tracker.issues(id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES issue_tracker.users(id) ON DELETE CASCADE
);



