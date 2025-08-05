create table t_us_task
(
    `id`         BIGINT,
    `task_id`    STRING,
    `properties` STRING,
    PRIMARY KEY (id) NOT ENFORCED
) with (
      'connector' = 'mysql-cdc',
      'hostname' = 'localhost',
      'port' = '3306',
      'username' = 'root',
      'password' = 'hwc26499773',
      'database-name' = 'mdb_nestorhuang',
      'table-name' = 't_us_task',
      'scan.startup.mode' = 'initial'
      );