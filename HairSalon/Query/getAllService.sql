USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAllService]    Script Date: 11.12.2023 01:28:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER     PROCEDURE [dbo].[getAllService]
AS
BEGIN
	SELECT
	   [id_service]
	  ,[name]
      ,[price]
      ,[minutes]
      ,[active]
      ,[id_group]
	FROM service WHERE active = 1;
END;