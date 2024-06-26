USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[addOrUpdateAppointment]    Script Date: 23.12.2023 01:25:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER   PROCEDURE [dbo].[addOrUpdateAppointment](
	 @IdAppointment int,
	 @IdCustomer int,
	 @IdStaff int,
	 @IdService int,
	 @Date date,
	 @SH int,
	 @SM int,
	 @EH int,
	 @EM int,
	 @Info varchar(500),
	 @price money,
	 @Active bit
)
AS
BEGIN
	IF @IdAppointment =-1
		INSERT INTO Appointment(id_customer, id_staff, id_service, date_app, start_hour, start_min, end_hour, end_min, info, price,active)
		VALUES (@IdCustomer, @IdStaff, @IdService, @Date, @SH, @SM, @EH, @EM, @Info, @price,@Active);
	ELSE
		UPDATE Appointment SET
		id_customer = @IdCustomer,
		id_staff = @IdStaff,
		id_service = @IdService,
		date_app = @Date,
		start_hour = @SH,
		start_min = @SM,
		end_hour = @EH,
		end_min = @EM,
		info = @Info,
		price = @price,
		active = @Active
		WHERE id_appointment = @IdAppointment;
END