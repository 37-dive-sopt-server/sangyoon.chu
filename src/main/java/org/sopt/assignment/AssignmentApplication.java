package org.sopt.assignment;

import org.sopt.assignment.Handler.InputHandler;
import org.sopt.assignment.Handler.OutputHandler;
import org.sopt.assignment.config.AppConfig;
import org.sopt.assignment.controller.MemberController;
import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;
import org.sopt.assignment.exception.ExceptionHandler;
import org.sopt.assignment.validator.MemberInputValidator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();

        MemberController memberController = appConfig.memberController();
        InputHandler inputHandler = appConfig.inputHandler();
        OutputHandler outputHandler = appConfig.outputHandler();

        while (true) {
         try {
             outputHandler.displayMenu();
             String choice = inputHandler.readMenuChoice();

             switch (choice) {
                 case "1":
                     registerMember(inputHandler, outputHandler, memberController);
                     break;
                 case "2":
                     findMemberById(inputHandler, outputHandler, memberController);
                     break;
                 case "3":
                     displayAllMembers(outputHandler, memberController);
                     break;
                 case "4":
                     deleteMember(inputHandler, outputHandler, memberController);
                     break;
                 case "5":
                     outputHandler.displayQuitMenu();
                     inputHandler.close();
                     return;
                 default:
                     throw BaseException.type(ErrorCode.INVALID_MENU_NUMBER);
             }
         } catch (BaseException e) {
             ExceptionHandler.handle(e);
         }
        }
    }

    private static void registerMember(InputHandler inputHandler,
                                       OutputHandler outputHandler,
                                       MemberController memberController) {
        try {
            String name =  inputHandler.readName();
            MemberInputValidator.validateName(name);

            String email = inputHandler.readEmail();
            MemberInputValidator.validateEmail(email);

            String userBirthday = inputHandler.readBirthday();
            LocalDate birthday = LocalDate.parse(userBirthday);
            MemberInputValidator.validateBirthday(birthday);

            String userGender = inputHandler.readGender();
            Gender gender = Gender.fromInput(userGender);

            Long createdId = memberController.createMember(name, email, birthday, gender);
            outputHandler.displayMemberRegistered(createdId);

        } catch (BaseException | DateTimeParseException e) {
            ExceptionHandler.handle(e);
        }
    }

    private static void findMemberById(InputHandler inputHandler,
                                       OutputHandler outputHandler,
                                       MemberController memberController) {
        try {
            Long id = Long.parseLong(inputHandler.readMemberId());
            var member = memberController.findMemberById(id);
            outputHandler.displayMember(member);
            } catch (BaseException | NumberFormatException e) {
                ExceptionHandler.handle(e);
            }
    }

    private static void displayAllMembers(OutputHandler outputHandler,
                                          MemberController memberController) {
        var allMembers = memberController.getAllMembers();
        outputHandler.displayAllMembers(allMembers);
    }

    private static void deleteMember(InputHandler inputHandler,
                                     OutputHandler outputHandler,
                                     MemberController memberController) {
        try {
            Long id = Long.parseLong(inputHandler.readMemberId());
            String deletedName = memberController.deleteMember(id);
            outputHandler.displayMemberDeleted(deletedName);
        } catch (NumberFormatException | BaseException e) {
            ExceptionHandler.handle(e);
        }
    }
}