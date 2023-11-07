CREATE TABLE `hrm`.`team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `team_lead_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `team_lead_id_fk_idx` (`team_lead_id` ASC) VISIBLE,
  CONSTRAINT `team_lead_id_fk`
    FOREIGN KEY (`team_lead_id`)
    REFERENCES `hrm`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);