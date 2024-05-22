USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[deleteService]    Script Date: 11.12.2023 01:30:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[deleteService]	
(
 @id integer
)
AS
BEGIN
	UPDATE service
	SET active = 0
	WHERE id_service = @id;
END;