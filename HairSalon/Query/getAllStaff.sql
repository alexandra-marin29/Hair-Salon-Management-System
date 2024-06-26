USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAllStaff]    Script Date: 20.12.2023 10:12:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getAllStaff]
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

FROM [staff] s 
INNER JOIN 
        [StaffRole] sr ON s.id_staff = sr.id_staff 
    WHERE 
        s.[active] = 1;
END;