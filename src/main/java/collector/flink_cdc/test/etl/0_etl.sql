CREATE TEMPORARY VIEW result_view (
    id,
    qualified_name,
    task_id,
    status,
    owner,
    platform,
    props,
    cur_run_time,
    create_at,
    table_lineage
) as
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
    table_lineage
from t_us_task;
