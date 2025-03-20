-- Insert sample members if they don't exist
INSERT INTO members (id, name, address, email, phone_number, membership_start_date, membership_duration)
SELECT 1, 'John Smith', '123 Golf Ave, Augusta, GA', 'john.smith@example.com', '555-123-4567', '2025-01-01', 12
    WHERE NOT EXISTS (SELECT 1 FROM members WHERE id = 1);

-- Insert sample tournaments if they don't exist
INSERT INTO tournaments (id, start_date, end_date, location, entry_fee, cash_prize_amount)
SELECT 1, '2025-04-15', '2025-04-18', 'Pebble Beach Golf Links', 150.00, 5000.00
    WHERE NOT EXISTS (SELECT 1 FROM tournaments WHERE id = 1);

-- Add members to tournaments only if association doesn't exist
INSERT INTO member_tournament (member_id, tournament_id)
SELECT 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM member_tournament WHERE member_id = 1 AND tournament_id = 1);