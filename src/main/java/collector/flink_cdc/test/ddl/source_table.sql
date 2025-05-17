create table t_us_task (
    `id`                INT,
    `qualified_name`    STRING,
    `task_id`           INT,
    `status`            STRING,
    `owner`             STRING,
    `platform`          STRING,
    `props`             STRING,
    `cur_run_time`      TIMESTAMP(3),
    `create_at`         TIMESTAMP(3),
    `table_lineage`     STRING,
    PRIMARY KEY (id) NOT ENFORCED
) with (
    'connector' = 'mysql-cdc',
    'hostname' = 'localhost',
    'port' = '3306',
    'username' = 'root',
    'password' = 'hwc26499773',
    'database-name' = 'mdb_nestorhuang',
    'table-name' = 'test_cdc',
    'scan.startup.mode' = 'latest-offset'
);