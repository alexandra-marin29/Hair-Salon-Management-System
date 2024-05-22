USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getCustomerById]    Script Date: 11.12.2023 01:25:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getCustomerById]
(
 @id integer
)
AS
BEGIN
	SELECT 
	   [id_customer]
	  ,[username]
      ,[first_name]
      ,[last_name]
      ,[tel]
      ,[info]
      ,[active]
	FROM customer WHERE id_customer=@id AND active=1;
END;