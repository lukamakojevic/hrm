ALTER TABLE `hrm`.`employee`
ADD COLUMN `team_id` INT NULL AFTER `name`,
ADD INDEX `team_id_fk_idx` (`team_id` ASC) VISIBLE;
ALTER TABLE `hrm`.`employee`
ADD CONSTRAINT `team_id_fk`
  FOREIGN KEY (`team_id`)
  REFERENCES `hrm`.`team` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
