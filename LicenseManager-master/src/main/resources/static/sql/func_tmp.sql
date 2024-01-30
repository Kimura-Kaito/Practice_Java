drop function func_tmp(integer, text, text, date);
create or replace function func_tmp(
	e_id integer,
	e_name text,
	e_department text,
	e_changeStatusDate date) returns integer
as $$
declare
	rec_dummy integer;
begin
	create table if not exists e_tmp(
	id integer primary key,
	name text not null,
	department text,
	change_status_date date
	);
	insert into e_tmp(id, name, department, change_status_date)
	values(e_id, e_name, e_department, e_changeStatusDate);
	
	return rec_dummy;
end;
$$
language plpgsql;