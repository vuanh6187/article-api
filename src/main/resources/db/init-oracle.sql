-- (optional) create a dedicated article_api user if you want a separate schema.
-- Run this as a privileged Oracle user connected to service name xepdb1.

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

create user article_api identified by "<set-password-before-running>"
    default tablespace users
    temporary tablespace temp
    quota unlimited on users;

grant connect, resource to article_api;
grant create view to article_api;
grant create sequence to article_api;

-- If you use a different application schema, this script is not required.
