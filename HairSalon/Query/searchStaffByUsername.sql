USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[searchStaffByUsername]    Script Date: 20.12.2023 15:47:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[searchStaffByUsername](@Username varchar(25))
AS
BEGIN
	SELECT
	   [id_staff]
      ,[first_name]
      ,[last_name]
      ,[tel]
      ,[info]
      ,[active]
	  ,[username]
      ,[password]
	FROM staff WHERE
	active = 1 and username = @Username;
END;