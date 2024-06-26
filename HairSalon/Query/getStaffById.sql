USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getStaffById]    Script Date: 23.12.2023 12:42:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER    PROCEDURE [dbo].[getStaffById]
(
 @id integer
)
AS
BEGIN
    SELECT 
        s.[id_staff],
        s.[username],
        s.[password],
        s.[first_name],
        s.[last_name],
        s.[tel],
        s.[info],
        s.[active],
        sr.[id_role]
    FROM staff s
    INNER JOIN StaffRole sr ON s.id_staff = sr.id_staff
    WHERE s.id_staff = @id AND s.active = 1;
END;