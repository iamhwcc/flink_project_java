create table table_2_table_test
(
    `id`         BIGINT,
    `task_id`    STRING,
    `src_table_name` STRING,
    PRIMARY KEY (task_id, src_table_name) NOT ENFORCED
) with
(
    'connector' = 'jdbc',
    'url' = 'jdbc:mysql://localhost:3306/mdb_nestorhuang',
    'username' = 'root',
    'password' = 'hwc26499773',
    'table-name' = 'table_2_table_test',
    'sink.buffer-flush.max-rows' = '1',
    'sink.buffer-flush.interval' = '0s',
    'sink.max-retries' = '0'
)