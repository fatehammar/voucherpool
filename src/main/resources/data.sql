-- Insert Recipients
INSERT INTO recipients (name, email) VALUES
('John Wick', 'john.wick@example.com'),
('Saitama Op', 'saitama.op@example.com'),
('Alice Kagura', 'alice.kaguna@example.com'),
('Chicken Dinner', 'chick.dine@example.com'),
('Roronoa Zoro', 'roronoa.zoro@example.com');

-- Insert Special Offers
INSERT INTO special_offers (name, percentage_discount, description) VALUES
('Summer Sale', 25.0, 'Get 25% off on all summer items'),
('Welcome Bonus', 15.0, 'New customer discount'),
('Holiday Special', 30.0, 'Special holiday season discount'),
('Birthday Offer', 20.0, 'Birthday month special discount'),
('Flash Sale', 40.0, 'Limited time flash sale discount');

-- Insert Voucher Codes
INSERT INTO voucher_codes (code, recipient_id, special_offer_id, used, expiration_date) VALUES
('SUMMER2025', 1, 1, false, '2025-12-31 23:59:59'),
('WELCOME15', 2, 2, false, '2025-12-31 23:59:59'),
('HOLIDAY30', 3, 3, false, '2025-12-31 23:59:59'),
('BDAY2025', 4, 4, false, '2025-12-31 23:59:59'),
('FLASH404', 5, 5, false, '2025-12-31 23:59:59'),
('SUMMER25A', 1, 1, false, '2025-12-31 23:59:59'),
('WELCOME1A', 2, 2, false, '2025-12-31 23:59:59'),
('HOLIDAY3A', 3, 3, false, '2025-12-31 23:59:59'),
('BDAY25A1', 4, 4, false, '2025-12-31 23:59:59'),
('FLASH4A1', 5, 5, false, '2025-12-31 23:59:59');
