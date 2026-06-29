CREATE TABLE IF NOT EXISTS `cms_recommended` (
  `recommended_id` int(11) NOT NULL,
  `type` enum('GROUP','ROOM') NOT NULL,
  `is_staff_pick` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `cms_recommended`
  MODIFY COLUMN `type` enum('GROUP','ROOM') NOT NULL,
  ADD COLUMN IF NOT EXISTS `is_staff_pick` tinyint(1) NOT NULL DEFAULT 0;

ALTER TABLE `cms_recommended`
  ADD INDEX `idx_cms_recommended_lookup` (`type`, `is_staff_pick`, `recommended_id`);
