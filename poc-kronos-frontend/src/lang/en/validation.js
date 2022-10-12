const validation = {
  required: "Required",
  email: {
    required: "Email field is required",
    email: "Email must be valid",
    alreadyExist: "Email is already register",
    notExist: "This email is not registered with us"
  },
  password: {
    required: "Password is required",
    confirm: "Password does not match",
    min: "At least 8 characters required",
    max: "Max 32 characters",
    validPassword:
      "Password must have [A-Z], [a-z], [0-9] and one special character"
  },
  username: {
    required: "Username field is required",
    uniqueUser: "Username must be valid",
    min: "Username must have at least four characters",
    max: "Username must not be more than 40 characters",
    alreadyExist: "Username is already taken"
  },
  verificationCode: {
    required: "Verification code is required"
  },
  userprofile: {
    first: {
      required: "First name is required",
      minLength: "First name must have at least two characters",
      maxLength: "First name must not be more than 60 characters"
    },
    last: {
      required: "Last name is required",
      minLength: "Last name must have at least two characters",
      maxLength: "Last name is not more than 60 characters"
    },
    phone: {
      maxLength: "Max 15 characters",
      minLength: "At least 7 characters are required",
      valid: "Phone number must be valid"
    },
    skype: {
      maxLength: "Max 32 characters",
      minLength: "At least 2 characters are required"
    },
    professional_title: {
      maxLength: "Max 60 characters"
    },
    country: {
      valid: `Invalid country name.`,
      maxLength: "Max 120 characters",
      minLength: "At least 2 characters are required"
    },
    city: {
      valid: `Invalid city name.`,
      maxLength: "Max 120 characters",
      minLength: "At least 2 characters are required"
    }
  },
  credentialNotMatch: "Could not match credentials",
  lang: {
    required: "Choose your language"
  },
  file: {
    size: "File is too large",
    profileSize: "Image size must be less than 25MB"
  }
};

export default validation;
