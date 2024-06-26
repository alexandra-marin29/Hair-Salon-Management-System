USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[loginStaff]    Script Date: 20.12.2023 09:34:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[loginStaff](@Username varchar(20), @Password varchar(20))
AS
BEGIN
	SELECT
	   s.[id_staff]
	  ,s.[username]
      ,s.[password]
      ,s.[first_name]
      ,s.[last_name]
      ,s.[tel]
      ,s.[info]
      ,s.[active]
	  ,sr.[id_role]
	FROM Staff s
    INNER JOIN StaffRole sr ON s.id_staff = sr.id_staff 
    WHERE
        s.active = 1 AND
        s.username = @Username AND
        s.password = @Password;
END;