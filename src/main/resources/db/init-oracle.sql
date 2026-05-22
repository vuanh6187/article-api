-- (tuy chon) tao user rieng article_api neu muon tach schema khoi system
-- chay bang sql developer: connect system/vuanh6187 -> service name xepdb1 -> run script (f5)

alter session set container = xepdb1;

begin
    execute immediate 'drop user article_api cascade';
exception
    when others then
        if sqlcode != -01918 then
            raise;
        end if;
end;
/

create user article_api identified by article_api
    default tablespace users
    temporary tablespace temp
    quota unlimited on users;

grant connect, resource to article_api;
grant create view to article_api;
grant create sequence to article_api;

-- neu dung system cho app thi khong can script nay.
-- hibernate se tu tao bang app_users trong schema system khi chay ung dung.
