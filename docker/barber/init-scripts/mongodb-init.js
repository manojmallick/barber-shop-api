var barbers =[
                {
                   "_id":ObjectId(),
                   "name":"Rafael",
                   "experience":"5",
                   "avatar":"https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png",
                   "schedules":[
                      {
                         "dayOfWeek":"MONDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"TUESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"WEDNESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"THURSDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"FRIDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"SATURDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      },
                      {
                         "dayOfWeek":"SUNDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      }
                   ]
                },
                {
                   "_id":ObjectId(),
                   "name":"Hasan",
                   "experience":"3",
                   "avatar":"https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png",
                   "schedules":[
                      {
                         "dayOfWeek":"MONDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"TUESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"WEDNESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"THURSDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"FRIDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"SATURDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      },
                      {
                         "dayOfWeek":"SUNDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      }
                   ]
                },
                {
                   "_id":ObjectId(),
                   "name":"Justin",
                   "experience":"2",
                   "avatar":"https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png",
                   "schedules":[
                      {
                         "dayOfWeek":"MONDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"TUESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"WEDNESDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"THURSDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"FRIDAY",
                         "startTime":"9:00",
                         "endTime":"17:00"
                      },
                      {
                         "dayOfWeek":"SATURDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      },
                      {
                         "dayOfWeek":"SUNDAY",
                         "startTime":"12:00",
                         "endTime":"16:00"
                      }
                   ]
                }
             ];


db.createCollection("barbers");

for (var i=0; i<barbers.length; i++) {
    var barber = barbers[i];

    db.barbers.insert({
        "_id": barber._id,
        "name": barber.name,
        "experience": barber.experience,
        "avatar": barber.avatar,
        "schedules":barber.schedules
    });
}

var services = [
                  {
                     "_id":ObjectId(),
                     "name":"Signature Haircut - Master Barber",
                     "amount":36,
                     "type":"CUT"
                  },
                  {
                     "_id":ObjectId(),
                     "name":"Young Gents - HairCut",
                     "amount":27.50,
                     "type":"CUT"
                  },
                  {
                     "_id":ObjectId(),
                     "name":"Buzz cut",
                     "amount":22.50,
                     "type":"CUT"
                  },
                  {
                     "_id":ObjectId(),
                     "name":"Express Beard Trim",
                     "amount":22.50,
                     "type":"SHAVING"
                  },
                  {
                     "_id":ObjectId(),
                     "name":"Faro Steam Shave",
                     "amount":27.50,
                     "type":"SHAVING"
                  },
                  {
                     "_id":ObjectId(),
                     "name":"Hot Towel Shave",
                     "amount":35,
                     "type":"SHAVING"
                  }
               ];


db.createCollection("services");

for (var i=0; i<services.length; i++) {
    var service= services[i];

    db.services.insert({
        "_id": service._id,
        "name": service.name,
        "amount": service.amount,
        "type": service.type,
    });
}


var shops = [
               {
                  "_id":ObjectId(),
                  "name":"Edwin's Barber Shop",
                  "address":"Hamontstraat 223,Amsterdam, North Holland",
                  "avatar":"https://i.pinimg.com/originals/96/2d/9a/962d9ab708a5f2998ae6538810d34b84.jpg",
                  "phone":"0790340932",
                  "email":"edwin.amsterdam@gmail.com",
                  "schedules":[
                     {
                        "dayOfWeek":"MONDAY",
                        "startTime":"9:00",
                        "endTime":"17:00"
                     },
                     {
                        "dayOfWeek":"TUESDAY",
                        "startTime":"9:00",
                        "endTime":"17:00"
                     },
                     {
                        "dayOfWeek":"WEDNESDAY",
                        "startTime":"9:00",
                        "endTime":"17:00"
                     },
                     {
                        "dayOfWeek":"THURSDAY",
                        "startTime":"9:00",
                        "endTime":"17:00"
                     },
                     {
                        "dayOfWeek":"FRIDAY",
                        "startTime":"9:00",
                        "endTime":"17:00"
                     },
                     {
                        "dayOfWeek":"SATURDAY",
                        "startTime":"12:00",
                        "endTime":"16:00"
                     },
                     {
                        "dayOfWeek":"SUNDAY",
                        "startTime":"12:00",
                        "endTime":"16:00"
                     }
                  ]
               }
            ];

db.createCollection("shops");

for (var i=0; i<shops.length; i++) {
    var shop= shops[i];

    db.shops.insert({
        "_id": shop._id,
        "name": shop.name,
        "address": shop.address,
        "avatar": shop.avatar,
        "phone": shop.phone,
        "email": shop.email,
        "schedules":shop.schedules
    });
};