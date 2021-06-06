CREATE TABLE `test_master`.`user`
(
    `id`       INT                                 NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45)                         NULL,
    `email`    VARCHAR(45) CHARACTER SET 'utf8mb4' NULL,
    `birthday` DATE                                NULL,
    PRIMARY KEY (`id`)
);
