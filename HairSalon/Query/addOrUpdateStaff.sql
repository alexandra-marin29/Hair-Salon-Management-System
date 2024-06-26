USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[addOrUpdateStaff]    Script Date: 23.12.2023 15:58:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[addOrUpdateStaff](
    @IdStaff int,
    @FName varchar(25),
    @LName varchar(25),
    @Tel varchar(25),
    @Info varchar(500),
    @Username varchar(25),
    @Password varchar(1024),
    @Active bit,
    @IdRole int
)
AS
BEGIN
    IF @IdStaff = -1
    BEGIN
        INSERT INTO staff(username, password, first_name, last_name, tel, info, active) 
        VALUES (@Username, @Password, @FName, @LName, @Tel, @Info, @Active);

        DECLARE @NewStaffId int = SCOPE_IDENTITY();
        INSERT INTO StaffRole(id_staff, id_role) VALUES (@NewStaffId, @IdRole);
    END
    ELSE
    BEGIN
        UPDATE staff SET
            username = @Username,
            first_name = @FName,
            last_name = @LName,
            tel = @Tel,
            info = @Info,
            active = @Active
        WHERE id_staff = @IdStaff;

        IF @Password is not null AND LEN(@Password) > 3
            UPDATE staff SET password = @Password WHERE id_staff = @IdStaff;

        UPDATE StaffRole SET id_role = @IdRole WHERE id_staff = @IdStaff;
    END
END
