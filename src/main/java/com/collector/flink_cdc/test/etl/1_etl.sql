insert into table_2_table_test
select
    id,
    qualified_name,
    dst_tables,
    src_table
from
(
    select
        id,
        qualified_name,
        task_id,
        status,
        owner,
        platform,
        props,
        cur_run_time,
        create_at,
        JSON_VALUE(table_lineage, '$.src_table') as src_tables_ori,
        JSON_VALUE(table_lineage, '$.dst_table') as dst_tables,
        t.src_table                              as src_table
    from result_view
    left join lateral table (Arr2Tables(SplitFunction(JSON_VALUE(table_lineage, '$.src_table'), ','))) t(src_table) on true
    where table_lineage is not null
) a;


