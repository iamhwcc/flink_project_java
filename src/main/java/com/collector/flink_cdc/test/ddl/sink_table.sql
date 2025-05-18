create table table_2_table_test (
    `id`                INT,
    `qualified_name`    STRING,
    `src_table`           STRING,
    `dst_table`           STRING,
    PRIMARY KEY (id) NOT ENFORCED
) with (
    'connector' = 'jdbc',
    'url' = 'jdbc:mysql://localhost:3306/mdb_nestorhuang',
    'username' = 'root',
    'password' = 'hwc26499773',
    'table-name' = 'table_2_table_test'
)