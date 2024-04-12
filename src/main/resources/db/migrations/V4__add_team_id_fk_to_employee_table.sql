ALTER TABLE `employee`
ADD COLUMN `team_id` INT NULL AFTER `name`;

CREATE INDEX `team_id_fk_idx` ON `employee`(`team_id` ASC);

ALTER TABLE `employee`
ADD CONSTRAINT `team_id_fk`
  FOREIGN KEY (`team_id`)
  REFERENCES `team` (`id`)
  ON DELETE SET NULL
  ON UPDATE CASCADE;
