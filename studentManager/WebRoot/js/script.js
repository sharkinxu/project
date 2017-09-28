// user
$(document).ready(
		function() {
			
			var user_Boolean = false;
			var password_Boolean = false;
			var varconfirm_Boolean = false;
			var yanzheng_Boolean = false;

			$('.reg_user').blur(function() {
				if ((/^[a-z0-9_-]{4,8}$/).test($(".reg_user").val())) {
					$('.user_hint').html("✔").css("color", "green");
					user_Boolean = true;
				} else {
					$('.user_hint').html("×").css("color", "red");
					$(this).val("");
					user_Boolean = false;
				}
			});
			// password
			$('.reg_password').blur(function() {
				if ((/^[a-z0-9_-]{6,16}$/).test($(".reg_password").val())) {
					$('.password_hint').html("✔").css("color", "green");
					password_Boolean = true;
				} else {
					$('.password_hint').html("×").css("color", "red");
					password_Boolean = false;
				}
			});

			// password_confirm
			$('.reg_confirm').blur(function() {
				if (($(".reg_password").val()) == ($(".reg_confirm").val())) {
					$('.confirm_hint').html("✔").css("color", "green");
					varconfirm_Boolean = true;
				} else {
					$('.confirm_hint').html("×").css("color", "red");
					varconfirm_Boolean = false;
				}
			});

//			$("#input").blur(function() {
//				var inputs = $("#input").val().toUpperCase();
//				// alert(inputs);
//				if (codes.toUpperCase() == inputs) {
//					$('.yanzheng_hint').html("✔").css("color", "green");
//					yanzheng_Boolean = true;
//				} else {
//					$('.yanzheng_hint').html("×").css("color", "red");
//					yanzheng_Boolean = false;
//					
//					
//				}
//			});

			$('.red_button').click(
					function() {
						if (user_Boolean && password_Boolean
								&& varconfirm_Boolean
								&& yanzheng_Boolean == true) {
							alert("注册成功");
						} else {
							alert("请完善信息");
							return false;
						}
					});
			$("#input").blur(function() {
				var varyCode= $(this).val();
				
					$.ajax({
						url : "user?type=contrast&varyCode="+varyCode,
						type : "post",
						cache : false,
						data : {
							
						},
						processData : false,
						contentType : false,
						dataType : "text",
						success : function(data) {
							if (data == "success") {
								$(".yanzheng_hint").html("✔").css("color", "green");
								yanzheng_Boolean = true;
							} else {
								$(".yanzheng_hint").html("×").css("color", "red");
								yanzheng_Boolean = false;
								$("#input").val("");
							}
						}
					});
				});
			
			
			
		});