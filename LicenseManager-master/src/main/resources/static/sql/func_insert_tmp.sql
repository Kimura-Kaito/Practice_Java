drop function func_insert_tmp(INTEGER, INTEGER, DATE, DATE, TEXT, TEXT);
create or replace function func_insert_tmp(
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
	insert into el_tmp(employee_id, license_id, get_date, notification_date, start_date, remarks)
	values(employee, license, getdate, notificationdate, startdate, formremarks);
	
	return rec_dummy;
end;
$$
language plpgsql;