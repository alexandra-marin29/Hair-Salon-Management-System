USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[deleteStaff]    Script Date: 05.01.2024 13:05:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[deleteStaff]
(
 @id integer
)
AS
BEGIN
	UPDATE staff
	SET active = 0
	WHERE id_staff = @id;
END;