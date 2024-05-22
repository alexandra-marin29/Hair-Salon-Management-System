USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getServiceById]    Script Date: 19.12.2023 23:05:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getServiceById]
(
 @id integer
)
AS
BEGIN
	SELECT
	   [id_service]
	  ,[name]
      ,[price]
      ,[minutes]
      ,[active]
 
	FROM service WHERE id_service=@id AND active=1;
END;