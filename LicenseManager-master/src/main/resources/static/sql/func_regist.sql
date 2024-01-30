drop function func_regist(INTEGER, INTEGER, DATE, DATE, TEXT, TEXT);
create or replace function func_regist(
	employee INTEGER,
	license INTEGER,
	getdate DATE,
	notificationdate DATE,
	startdate TEXT,
	formremarks TEXT) returns INTEGER
as $$
declare
	rec_dummy INTEGER;
begin
	select count(*) into rec_dummy from employee_license
		where employee_id = employee AND license_id = license;
	if rec_dummy = 0 then
		insert into employee_license(employee_id,license_id,get_date,notification_date,start_date,remarks)
			values(employee, license, getdate, notificationdate, startdate, formremarks);
	else
	update employee_license
			set (get_date,notification_date,start_date,remarks) =
			(getdate, notificationdate, startdate, formremarks)
			where employee_id = employee AND license_id = license;
	end if;
	
	return rec_dummy;
end;
$$
language plpgsql;