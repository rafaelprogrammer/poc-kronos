const messages = {
  profileUpdated: "User profile updated",
  passwordUpdated: "Password succesfully changed",
  company: {
    login: "Company login succesfully",
    alreadyLogin: "Comapny already login",
    create: "Company succesfully created",
    forgot: {
      name: "Company name(s) were sent to your e-mail account",
      password: "Your password has been succesfully reset"
    }
  },
  invitation: {
    sent: "Invitation sent",
    resent: "Invitation resent",
    revoke: "Invitation revoked"
  },
  companySettingUpdate: "Company Settings updated",
  profileSettingUpdate: "Profile updated",
  accountSettingUpdate: "Account Settings updated",
  notificationSettingUpdate: "Notification settings updated",
  fileupload: {
    dictFileTooBig: "File is too large",
    dictFileTooSmall: "File is too small",
    dictCancelUploadConfirmation: "Are you sure?"
  },
  users: {
    deleteConfirmation:
      "Are you sure you want to delete the user? <br> Once deleted, the user will not be able to join the company anymore."
  },
  member: {
    searchNoMatchFound: "Could not find member"
  }
};

export default messages;
