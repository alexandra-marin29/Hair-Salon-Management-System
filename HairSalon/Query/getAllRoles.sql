USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAllRoles]    Script Date: 23.12.2023 15:45:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER     PROCEDURE [dbo].[getAllRoles]
AS
BEGIN
	SELECT
	   [id_role]
	  ,[name]
      ,[active]

	FROM Role WHERE active = 1;
END;