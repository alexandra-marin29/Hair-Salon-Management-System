USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getServiceByName]    Script Date: 11.12.2023 01:19:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getServiceByName]
(
 @Name varchar(50)
)
AS
BEGIN
	SELECT
	   [id_service]
	  ,[name]
      ,[price]
      ,[minutes]
      ,[active]
      ,[id_group]
	FROM service WHERE name=@Name AND active=1;
END;