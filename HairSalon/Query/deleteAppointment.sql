USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[deleteAppointment]    Script Date: 08.01.2024 19:24:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[deleteAppointment]
(
 @id integer
)
AS
BEGIN
	UPDATE Appointment 
	SET active=0
	WHERE id_appointment = @id;
END;