import React from 'react';

const postPositionSpreader = (posts) => {
  const set = new Set();

  posts.forEach(post => {
    let latitude = post.latitude;
    let longitude = post.longitude;

    while (set.has(makePositionString(latitude, longitude))) {
      latitude = post.latitude + getRandom();
      longitude = post.longitude + getRandom();
    }
    post.latitude = latitude;
    post.longitude = longitude;
    set.add(makePositionString(latitude, longitude));
  })
  return posts;
};

const makePositionString = (latitude, longitude) => latitude + "," + longitude

const getRandom = () => {
  const max =  0.0001;
  const min = -0.0001;
  const positiveMin =  0.00001;
  const negativeMax = -0.00001;
  const improvement = 0.00005;

  let random = Math.random() * (max - min) + min;

  if (0 < random && random < positiveMin) {
    random += improvement;
  } else if (negativeMax < random && random < 0) {
    random -= improvement;
  }
  return random;
}

export default postPositionSpreader;