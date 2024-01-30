drop function func_regist_all(integer);
create or replace function func_regist_all(integer) returns integer
as $$
declare
	rec_dummy integer;
begin
	insert into employee_license(
	employee_id,
	license_id,
	get_date,
	notification_date,
	start_date,
	remarks)
	select el_tmp.employee_id, 
		el_tmp.license_id, 
		el_tmp.get_date, 
		el_tmp.notification_date, 
		el_tmp.start_date, 
		el_tmp.remarks 
		from (select employee_id, license_id from el_tmp
		except
			(select employee_id, license_id from employee_license))as el_except, el_tmp
			where el_tmp.employee_id = el_except.employee_id and el_tmp.license_id = el_except.license_id;
	update employee_license
		set (get_date, 
		notification_date, 
		start_date, 
		remarks) =
		(el_tmp.get_date, 
		el_tmp.notification_date, 
		el_tmp.start_date, 
		el_tmp.remarks)
		from el_tmp
		where employee_license.employee_id = el_tmp.employee_id 
			and employee_license.license_id = el_tmp.license_id;
		
	truncate table el_tmp restart identity;
	
	return rec_dummy;
end;
$$
language plpgsql;
