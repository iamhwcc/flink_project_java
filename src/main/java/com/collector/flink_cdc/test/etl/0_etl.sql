CREATE TEMPORARY VIEW result_view as
select
    id
    , task_id
    , src_table_name
from
(
    select
        id,
        task_id,
        src_table_name
    from
    (
        select
            id,
            task_id,
            JsonArray2StringArray(json_query(properties, '$.srcTableNames')) as src_table_names
        from t_us_task
    )
    cross join unnest(src_table_names) t(src_table_name)
)
