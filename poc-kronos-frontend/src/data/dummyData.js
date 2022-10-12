// import moment from 'moment'
export const users = [
  {
    id: 1,
    name: "Alice Blue",
    firstname: "Alice",
    lastname: "Blue",
    email: "aliceblue@example.com",
    avatar: `/static/doc-images/lists/ali.jpg`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "success",
      icon: "check_circle"
    }
  },
  {
    id: 2,
    name: "Jack Johnson",
    firstname: "Jack",
    lastname: "Johnson",
    email: "jack@example.com",
    avatar: `/static/doc-images/lists/men1.png`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "success",
      icon: "check_circle"
    }
  },
  {
    id: 3,
    name: "Bruce Canta",
    firstname: "Bruce",
    lastname: "Canta",
    email: "bruce@example.com",
    avatar: `/static/doc-images/lists/jack.png`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "warning",
      icon: "access_time"
    }
  },
  {
    id: 4,
    name: "Camelia Lopez",
    firstname: "Camelia",
    lastname: "Lopez",
    email: "camelia@example.com",
    avatar: `/static/doc-images/lists/julieta.png`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "grey",
      icon: "not_interested"
    }
  },
  {
    id: 5,
    name: "Denis Richard",
    firstname: "Denis",
    lastname: "Richard",
    email: "denis@example.com",
    avatar: `/static/doc-images/lists/2.jpg`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "secondary",
      icon: "stars"
    }
  },
  {
    id: 6,
    name: "Mia Willson",
    firstname: "Mia",
    lastname: "Willson",
    email: "mia@example.com",
    avatar: `/static/doc-images/lists/lily.png`,
    mood: "Vuse - Powerful VuejS admin template.",
    status: {
      color: "success",
      icon: "check_circle"
    }
  }
];

export const authUser = users[0];

export const conversation = [
  {
    id: 1,
    group_id: 1,
    user_id: 2,
    user: users[1],
    message:
      "We are planning to move with GrapQL API with Board section. What do you think on it",
    created_at: 1528104070
  },
  {
    id: 2,
    group_id: 1,
    user_id: 2,
    user: users[1],
    message:
      "I mean for scrum board. A part from this, I have design some layouts in Sketch for it. Did you get chance to see those?",
    created_at: 1528104071
  },
  {
    id: 3,
    group_id: 1,
    user_id: 1,
    user: authUser,
    message: `Yeah, that's really sounds good. I loved it. Can you please connet with Mia for further implementation?`,
    created_at: 1528104072
  },
  {
    id: 4,
    group_id: 1,
    user_id: 2,
    user: users[1],
    message:
      "I am scheduling a meeting with Team today at 5:00PM. Hope you are available.",
    created_at: 1528104073
  },
  {
    id: 5,
    group_id: 1,
    user_id: 2,
    user: users[1],
    message:
      "I also need your assistance for GraphQL API structuring. Are you gonna make APIs in Laravel for it?",
    created_at: 1528104074
  },
  {
    id: 6,
    group_id: 2,
    user_id: 3,
    user: users[2],
    message: `I have started reviewing <b>MaterialCSS</b> framework. It seems that they have improved the Framework.`,
    created_at: 1528104074
  },
  {
    id: 7,
    group_id: 2,
    user_id: 3,
    user: users[2],
    message:
      "How do you plan for Forge Admin new version, we need to talk about his.",
    created_at: 1528104075
  },
  {
    id: 8,
    group_id: 2,
    user_id: 3,
    user: authUser,
    message:
      "Sure, we can arrage a meeting tomorrow in the morning. I have planned a few things. I will share you details tomorrow. Will it be fine?",
    created_at: 1528104076
  },
  {
    id: 9,
    group_id: 3,
    user_id: 3,
    user: users[3],
    message: `We are planning some activities in this weekends. So you can provide your inputs.`,
    created_at: 1528104075
  },
  {
    id: 10,
    group_id: 3,
    user_id: 3,
    user: users[3],
    message: `let's play Table-Tennis in the evening, after that we can discuss about the weekends suggestions. What do you say?`,
    created_at: 1528104076
  },
  {
    id: 11,
    group_id: 4,
    user_id: 4,
    user: users[4],
    message: `we have received a mail from Envato regarding new Taxation policy. Once you get chance to have look into that.`,
    created_at: 1528104076
  },
  {
    id: 12,
    group_id: 5,
    user_id: 5,
    user: users[5],
    message: `Forge Admin needs some improvements in designs. We have very nice features, but old design is factor of less sales.`,
    created_at: 1528104076
  },
  {
    id: 13,
    group_id: 5,
    user_id: 5,
    user: users[5],
    message: `We need to think about this.`,
    created_at: 1528104077
  },
  {
    id: 14,
    group_id: 5,
    user_id: 5,
    user: users[5],
    message: `As I know - Bruce is reviewing the details for MaterialCSS. Let me know once you planned a meeting.`,
    created_at: 1528104078
  }
];

export const groups = [
  {
    id: 1,
    user: {
      ...users[1],
      last_message: "Jan 20 , 2018"
    },
    unread: 2
  },
  {
    id: 2,
    user: {
      ...users[2],
      last_message: "Jan 20 , 2018"
    },
    unread: 0
  },
  {
    id: 3,
    user: {
      ...users[3],
      last_message: "Jan 20 , 2018"
    },
    unread: 2
  },
  {
    id: 4,
    user: {
      ...users[4],
      last_message: "Jan 20 , 2018"
    },
    unread: 0
  },
  {
    id: 5,
    user: {
      ...users[5],
      last_message: "Jan 20 , 2018"
    },
    unread: 3
  }
];

export const defaultUserPic = "/static/default/user.svg";
