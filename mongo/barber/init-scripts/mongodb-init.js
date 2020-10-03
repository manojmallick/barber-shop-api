var barbers = [
     {
        "_id": 1,
        "name": "Rafael",
        "experience": "5",
        "avatar": "https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png"
    },
    {
         "_id": 2,
         "name": "Hasan",
         "experience": "3",
         "avatar": "https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png"
    },
    {
          "_id": 3,
          "name": "Justin",
          "experience": "2",
          "avatar": "https://www.clker.com/cliparts/g/l/R/7/h/u/teamstijl-person-icon-blue-hi.png"
     }
];


db.createCollection("barbers");

for (var i=0; i<barbers.length; i++) {
    var barber = barbers[i];

    db.barbers.insert({
        "_id": barber._id,
        "name": barber.name,
        "experience": barber.experience,
        "avatar": barber.avatar
    });
}


var barber_schedules =[
                         {
                            "_id":1,
                            "dayOfWeek":1,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":1
                         },
                         {
                            "_id":2,
                            "dayOfWeek":2,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":1
                         },
                         {
                            "_id":3,
                            "dayOfWeek":3,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":1
                         },
                         {
                            "_id":4,
                            "dayOfWeek":4,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":1
                         },
                         {
                            "_id":5,
                            "dayOfWeek":5,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":1
                         },
                         {
                            "_id":6,
                            "dayOfWeek":6,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":1
                         },
                         {
                            "_id":7,
                            "dayOfWeek":7,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":1
                         },
                         {
                            "_id":8,
                            "dayOfWeek":1,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":2
                         },
                         {
                            "_id":9,
                            "dayOfWeek":2,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":2
                         },
                         {
                            "_id":10,
                            "dayOfWeek":3,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":2
                         },
                         {
                            "_id":11,
                            "dayOfWeek":4,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":2
                         },
                         {
                            "_id":12,
                            "dayOfWeek":5,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":2
                         },
                         {
                            "_id":13,
                            "dayOfWeek":6,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":2
                         },
                         {
                            "_id":14,
                            "dayOfWeek":7,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":2
                         },
                         {
                            "_id":15,
                            "dayOfWeek":1,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":3
                         },
                         {
                            "_id":16,
                            "dayOfWeek":2,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":3
                         },
                         {
                            "_id":17,
                            "dayOfWeek":3,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":3
                         },
                         {
                            "_id":18,
                            "dayOfWeek":4,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":3
                         },
                         {
                            "_id":19,
                            "dayOfWeek":5,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":3
                         },
                         {
                            "_id":20,
                            "dayOfWeek":6,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "barberId":3
                         },
                         {
                            "_id":21,
                            "dayOfWeek":7,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "barberId":3
                         }
                      ];

db.createCollection("barber_schedules");

for (var i=0; i<barber_schedules.length; i++) {
    var barber_schedule = barber_schedules[i];

    db.barber_schedules.insert({
        "_id": barber_schedule._id,
        "dayOfWeek": barber_schedule.dayOfWeek,
        "startTime": barber_schedule.startTime,
        "endTime": barber_schedule.endTime,
        "barberId": barber_schedule.barberId
    });
}

var services = [
                  {
                     "_id":1,
                     "name":"Signature Haircut - Master Barber",
                     "amount":36,
                     "type":"Cut"
                  },
                  {
                     "_id":2,
                     "name":"Young Gents - HairCut",
                     "amount":27.50,
                     "type":"Cut"
                  },
                  {
                     "_id":3,
                     "name":"Buzz cut",
                     "amount":22.50,
                     "type":"Cut"
                  },
                  {
                     "_id":4,
                     "name":"Express Beard Trim",
                     "amount":22.50,
                     "type":"Shave"
                  },
                  {
                     "_id":5,
                     "name":"Faro Steam Shave",
                     "amount":27.50,
                     "type":"Shave"
                  },
                  {
                     "_id":6,
                     "name":"Hot Towel Shave",
                     "amount":35,
                     "type":"Shave"
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


var customers = [
                   {
                      "_id":1,
                      "name":"Manoj",
                      "email":"manoj@gmail.com",
                      "gender":"male",
                      "mobile":"0656578473"
                   },
                   {
                      "_id":2,
                      "name":"Eric",
                      "email":"eric@gmail.com",
                      "gender":"male",
                      "mobile":"0656678438"
                   },
                   {
                      "_id":3,
                      "name":"Andrew",
                      "email":"andrew@gmail.com",
                      "gender":"male",
                      "mobile":"0655457843"
                   }
                ];

db.createCollection("customers");

for (var i=0; i<customers.length; i++) {
    var customer= customers[i];

    db.customers.insert({
        "_id": customer._id,
        "name": customer.name,
        "email": customer.email,
        "gender": customer.gender,
        "mobile": customer.mobile,
    });
}


var appointments = [
                   {
                      "_id":1,
                      "customerId":1,
                      "barberId":1,
                      "startTime":"09:30",
                      "endTime":"10:00",
                      "services":[1,2]
                   }
                ];

db.createCollection("appointments");

for (var i=0; i<appointments.length; i++) {
    var appointment= appointments[i];

    db.appointments.insert({
        "_id": appointment._id,
        "customerId": appointment.customerId,
        "barberId": appointment.barberId,
        "startTime": appointment.startTime,
        "endTime": appointment.endTime,
        "services": appointment.services
    });
}

var shops = [
                   {
                      "_id":1,
                      "name":"Edwin's Barber Shop",
                      "address":"Hamontstraat 223,Amsterdam, North Holland",
                      "avatar":"https://data.pixiz.com/output/user/frame/preview/400x400/2/2/5/8/3058522_ecf92.jpg",
                      "phone":"0790340932",
                      "email":"edwin.amsterdam@gmail.com"
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
        "email": shop.email
    });
};

var shop_schedules =[
                         {
                            "_id":1,
                            "dayOfWeek":1,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "shopId":1
                         },
                         {
                            "_id":2,
                            "dayOfWeek":2,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "shopId":1
                         },
                         {
                            "_id":3,
                            "dayOfWeek":3,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "shopId":1
                         },
                         {
                            "_id":4,
                            "dayOfWeek":4,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "shopId":1
                         },
                         {
                            "_id":5,
                            "dayOfWeek":5,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "shopId":1
                         },
                         {
                            "_id":6,
                            "dayOfWeek":6,
                            "startTime":"9:00",
                            "endTime":"17:00",
                            "shopId":1
                         },
                         {
                            "_id":7,
                            "dayOfWeek":7,
                            "startTime":"12:00",
                            "endTime":"16:00",
                            "shopId":1
                         }
                      ];
db.createCollection("shop_schedules");

for (var i=0; i<shop_schedules.length; i++) {
    var shop_schedule = shop_schedules[i];

    db.shop_schedules.insert({
        "_id": shop_schedule._id,
        "dayOfWeek": shop_schedule.dayOfWeek,
        "startTime": shop_schedule.startTime,
        "endTime": shop_schedule.endTime,
        "shopId": shop_schedule.shopId
    });
}