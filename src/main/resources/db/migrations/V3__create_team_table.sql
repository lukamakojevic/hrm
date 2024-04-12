CREATE TABLE `team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `team_lead_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `team_lead_id_fk`
    FOREIGN KEY (`team_lead_id`)
    REFERENCES `employee` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE INDEX `team_lead_id_fk_idx` ON `team`(`team_lead_id` ASC);

CREATE UNIQUE INDEX `team_lead_id_unique_idx` ON `team`(`team_lead_id` ASC);