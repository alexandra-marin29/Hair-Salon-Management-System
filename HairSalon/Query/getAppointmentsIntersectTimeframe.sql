USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAppointmentsIntersectTimeframe]    Script Date: 20.12.2023 15:46:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getAppointmentsIntersectTimeframe](@StaffId int, @Date date, @SH int, @SM int, @EH int, @EM int)
AS
BEGIN
	SELECT id_appointment
      ,id_customer
      ,id_staff
      ,id_service
      ,date_app
      ,start_hour
      ,start_min
      ,end_hour
      ,end_min
      ,info
      ,price
	FROM Appointment WHERE
	id_staff = @StaffId AND
	date_app = @Date AND
	(
	(
		(start_hour < @SH OR start_hour = @SH AND start_min < @SM) AND (@SH < end_hour OR @SH = end_hour AND @SM < end_min)
		OR
		(start_hour < @EH OR start_hour = @EH AND start_min < @EM) AND (@EH < end_hour OR @EH = end_hour AND @EM < end_min)
	)
	OR
	(
		(start_hour > @SH OR start_hour = @SH AND start_min >= @SM) AND (end_hour < @EH OR end_hour = @EH AND end_min <= @EM)
	)
	)
END;