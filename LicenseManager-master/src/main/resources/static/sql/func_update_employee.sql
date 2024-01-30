drop function func_update_employee(integer);
create or replace function func_update_employee(integer) returns integer
as $$
declare
	update_out integer;
begin
	update employee_license
	set employee_id = tmp_id.new_id
	from
		(select e_old.id old_id, e_new.id new_id
			from
			(select * from employee except (select * from e_tmp)) as e_old,
			(select * from e_tmp except (select * from employee)) as e_new
			where e_old.name = e_new.name and e_old.id >= 1000 and e_new.id < 1000)as tmp_id
	where employee_id = tmp_id.old_id;
	
	update employee
		set (id, department, change_status_date) =
			(e_tmp.id, e_tmp.department, e_tmp.change_status_date)
		from e_tmp
		where employee.name = e_tmp.name;
	
	insert into employee
		select e_tmp.*
			from (select id from e_tmp
				except
				(select id from employee)) as e_except, e_tmp
			where e_tmp.id = e_except.id;
	
	delete
		from employee
		where
			id in(
				select employee.id
					from (select id from employee
						except
						(select id from e_tmp)) as e_except2, employee
					where employee.id = e_except2.id);
	
	delete 
		from employee_license
		where employee_id not in (select id from employee);
		
	truncate table e_tmp restart identity;
	
	return update_out;
end;
$$
language plpgsql;